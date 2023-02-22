import {Button, TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import BarreNavCore from "../../Components/BarreNav/BarreNavCore";

function ModifyID() {
    const {register, handleSubmit} = useForm()
    const navigate = useNavigate()

    const onSubmit = async (datas) => {
        let token = sessionStorage.getItem("token");

        // Token session expired
        if (token === null) {
            navigate("/");
        }

        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`)

        const jsonBody = {newMail: datas.newMail}
        const requestOptions = {
            method: 'PATCH',
            headers: myHeaders,
            body: JSON.stringify(jsonBody),
            redirect: 'follow'
        };

        let response = await fetch("api/auth/changeMail", requestOptions);
        let json = await response.json();
        console.log(response)
        if (response.status >= 400) {
            window.alert("oops mail incorrect");
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
                    <Button href="/infoProfil">Annuler</Button><Button type="submit">Valider</Button><br/>
                </form>
            </div>
        </div>
    )
}

export default ModifyID;