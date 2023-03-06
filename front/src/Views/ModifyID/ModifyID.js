import {Button, TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useCallback, useState} from "react";
import {Modal} from "react-bootstrap";

/**
 * Page for change mail
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function ModifyID() {
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();
    const [fieldErrors, setFieldErrors] = useState({});
    const [showModal, setShowModal] = useState(false);

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

        if (datas.newMail !== datas.confirmMail) {
            setFieldErrors({"mailConfirm": "Les mails ne correspondent pas"});
            return
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

        const response = await fetch("api/profil/changeMail", requestOptions);
        if (response.status > 200) {
            if (response.status === 400) {
                setFieldErrors({"mail": "Le mail est attribué à un compte déjà existant"});
            } else {
                setFieldErrors({"server": "Une erreur inattendue est survenue, veuillez réessayer plus tard"});
            }
            return;
        }

        setShowModal(true)
    }

    const handlePaste = useCallback((event) => {
        event.preventDefault();
    }, [])

    const handleCloseModal = useCallback(() => {
        navigate("/infoProfil")
    }, [navigate])

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-7 p-5">
                <h1>Modification de l&lsquo;adresse mail du profil</h1>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <TextField label="Nouvelle adresse mail" type="email" variant="standard"
                               className="textfield" {...register("newMail")} required
                               error={Boolean(fieldErrors.mail)}
                               helperText={fieldErrors.mail}/><br/>
                    <TextField label="Confirmer l'adresse mail" type="email" variant="standard"
                               className="textfield" {...register("confirmMail")} required onPaste={handlePaste}
                               error={Boolean(fieldErrors.mailConfirm)}
                               helperText={fieldErrors.mailConfirm}/><br/>

                    <Button href="/infoProfil">Annuler</Button><Button type="submit">Valider</Button><br/>
                </form>
            </div>
            <Modal show={showModal} >
                <Modal.Header>
                    <Modal.Title>Changement de mail</Modal.Title>
                </Modal.Header>
                <Modal.Body>Un mail a été envoyé pour confirmer le nouveau mail</Modal.Body>
                <Modal.Footer>
                    <Button onClick={handleCloseModal}>
                        Fermer
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}

export default ModifyID;