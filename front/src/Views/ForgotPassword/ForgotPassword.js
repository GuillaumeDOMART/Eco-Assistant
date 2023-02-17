import {Button, TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import BarreNavCore from "../../Components/BarreNav/BarreNavCore";


function ForgotPassword(){
    const {register, handleSubmit} = useForm()
    const onSubmit = () => {
        //TODO
    }

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
               <div className="col-7 p-5">
                   <h1>Mot de passe Oublié</h1>
                   <form onSubmit={handleSubmit(onSubmit)}>
                       <TextField label="Nouveau mot de passe" type="password" variant="standard" className="textfield" {...register("newPassword")} required/><br/>
                       <TextField label="Confirmer le nouveau mot de passe" type="password" variant="standard" className="textfield" {...register("newPasswordConfirmed")} required/><br/>
                       <Button href="/profil">Annuler</Button><Button type="submit">Valider</Button><br/>
                   </form>
               </div>
        </div>
    )
}
export default ForgotPassword;