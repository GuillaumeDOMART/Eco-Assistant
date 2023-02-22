import {Button, TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import BarreNavCore from "../../Components/BarreNav/BarreNavCore";

/**
 * Page for change mail
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function ModifyID() {
    const {register, handleSubmit} = useForm()
    const navigate = useNavigate()

    const onSubmit = () => {
        //need to be finish
        navigate("/profil")
    }

    return (
       <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
           <BarreNavCore/>
           <div className="col-7 p-5">
               <h1>Modification de l&lsquo;identifiant</h1>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <TextField label="Nouvel identifiant" type="text" variant="standard" className="textfield" {...register("newID")} required/><br/>
                    <Button href="/infoProfil">Annuler</Button><Button type="submit">Valider</Button><br/>
                </form>
           </div>
       </div>
   )
}

export default ModifyID;