import {Button, TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import {useState} from "react";

/**
 * Page for change mail
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function ModifyID() {
    const {register, handleSubmit} = useForm()
    const navigate = useNavigate()
    const [paragraphContent, setParagraphContent] = useState("");

    /**
     * Function that will submit the new mail. If the mail is not correct or already used, an error 400 is returned
     *
     * @param datas data containing the new mail
     * @returns {Promise<void>}
     */
    const onSubmit = async (datas) => {
        const token = sessionStorage.getItem("token");

        // Token session expired
        if (token === null) {
            navigate("/");
        }

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`)

        const jsonBody = {mail: datas.newMail}
        const requestOptions = {
            method: 'PATCH',
            headers: myHeaders,
            body: JSON.stringify(jsonBody),
            redirect: 'follow'
        };

        const response = await fetch("api/auth/changeMail", requestOptions);
        const json = await response.json();
        if (response.status > 200) {
            if (response.status === 400) {
                setParagraphContent("Le mail n'est pas conforme ou est attribué à un compte déjà existant");
                return;
            } else {
                setParagraphContent("Une erreur innatendue est survenue, veuillez réessayer plus tard");
            }
            return;
        }

        sessionStorage.setItem("token", json.token);
        // Gestion erreur


        navigate("/infoProfil")
    }

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-7 p-5">
                <h1>Modification de l&lsquo;adresse mail du profil</h1>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <TextField label="Nouvel adresse mail" type="email" variant="standard"
                               className="textfield" {...register("newMail")} required/><br/>
                    <p className="text-danger w-100 h-auto">{paragraphContent}</p>
                    <Button href="/infoProfil">Annuler</Button><Button type="submit">Valider</Button><br/>
                </form>
            </div>
        </div>
    )
}

export default ModifyID;