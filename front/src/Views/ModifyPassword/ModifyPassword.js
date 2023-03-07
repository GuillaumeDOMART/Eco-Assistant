import {Button, TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import {useState} from "react";
import StrengthMeter from "../AccueilSite/StrengthMeter";

/**
 * Page for change password
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function ModifyPassword() {
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();
    const [fieldErrors, setFieldErrors] = useState({});
    /**
     * Function that will submit the new password. If the password is not correct, an error 400 is returned
     *
     * @param datas data containing the new password
     * @returns {Promise<void>}
     */
    const onSubmit = async (datas) => {
        const token = sessionStorage.getItem("token");
        if (token === null) {
            navigate("/");
        }

        if (datas.password !== datas.newPasswordConfirmed) {
            setFieldErrors({"passwordConfirm": "Les mot de passe fournis ne correspondent pas"})
            return
        }

        if (datas.actualPassword === datas.password) {
            setFieldErrors({"password": "Le nouveau mot de passe ne peut pas être le même que l'ancien"})
            return;
        }

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`)

        const jsonBody = {oldPassword: datas.actualPassword, password: datas.password}
        const requestOptions = {
            method: 'PATCH',
            headers: myHeaders,
            body: JSON.stringify(jsonBody),
            redirect: 'follow'
        }

        const response = await fetch("api/profil/changePassword", requestOptions);

        if (response.status > 200) {
            if (response.status === 400) {
                setFieldErrors({"password": "Le mot de passe n'est pas conforme (1 minuscule, 1 majuscule, 1 chiffre, 1 caractère spécial, longueur de 8 caractères minimum)"});
                return;
            } else {
                setFieldErrors({"actualPassword": "Ce n'est pas votre mot de passe actuel"});
            }
            return;
        }

        navigate("/infoProfil")
    }

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-7 p-5">
                <h1>Modification du mot de passe</h1>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <TextField label="Mot de passe actuel" type="password" variant="standard"
                               className="textfield" {...register("actualPassword")} required
                               error={Boolean(fieldErrors.actualPassword)}
                               helperText={fieldErrors.actualPassword}/>

                    <StrengthMeter register={register} fieldErrors={fieldErrors}></StrengthMeter>

                    <TextField label="Confirmer le nouveau mot de passe" type="password" variant="standard"
                               className="textfield" {...register("newPasswordConfirmed")} required
                               error={Boolean(fieldErrors.passwordConfirm)}
                               helperText={fieldErrors.passwordConfirm}/><br/>

                    <Button href="/infoProfil">Annuler</Button><Button type="submit">Valider</Button><br/>
                </form>
            </div>
        </div>
    )
}

export default ModifyPassword;