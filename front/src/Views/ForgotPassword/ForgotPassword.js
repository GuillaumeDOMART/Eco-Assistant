import {TextField} from "@mui/material";
import {useForm} from "react-hook-form";
import {Col, Container, Row, Modal, Button} from "react-bootstrap";
import {useCallback, useState} from "react";
import {useNavigate} from "react-router-dom";

/**
 * The component representing the page for forgot password
 * @returns {JSX.Element}
 * @constructor
 */
function ForgotPassword(){
    return (
        <>
            <Logo/>
            <Container className="vh-100 vw-100 d-flex align-items-center">
                <FormContainer/>
            </Container>
        </>

    )
}

/**
 * The component representing the logo of Eco Assistant
 * @returns {JSX.Element}
 * @constructor
 */
function Logo(){
    return (
        <img className="logo position-absolute start-0 top-0 m-1" src={require('../../Components/logo/logo.PNG')} alt={"logo"} style={{width:'10%',height: 'auto'}}/>
    );
}

/**
 * The component representing the form of connexion
 * @returns {JSX.Element}
 * @constructor
 */
function FormContainer(){
    return (
        <Container className= "d-flex align-items-center justify-content-center col-6 border border-5 border-secondary p-5 shadow-lg">
            <Col>
                <h1 style={{paddingBottom : "8%"}}>Mot de passe oublié</h1>
                <Form/>
            </Col>
        </Container>
    );
}

/**
 * Validation buttons of the form
 * @returns {JSX.Element}
 * @constructor
 */
function Buttons() {
    return(
        <Row>
            <Col>
                <Button href="/" variant="outline-danger">Annuler</Button><br/>
            </Col>
            <Col>
                <Button type="submit" variant= "outline-primary">Valider</Button><br/>
            </Col>
        </Row>
    )
}

/**
 * Component that represent the form
 * @returns {JSX.Element} return the form
 * @constructor default constructor
 */
function Form(){
    const [show, setShow] = useState(false);
    const {register, handleSubmit} = useForm()
    const [paragraphContent, setParagraphContent] = useState("")
    const navigate = useNavigate();

    /**
     * Function for the form
     */
    const onSubmit = (datas) => {
        if(datas.newPassword !== datas.newPasswordConfirmed){
            setParagraphContent("Les mots de passe fournis ne correspondent pas")
        }else {
            const token = new URLSearchParams(window.location.search).get('token');

            const myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");
            myHeaders.append("Authorization", `Bearer ${token}`);

            const requestOptions = {
                method: 'PATCH',
                headers: myHeaders,
                body: JSON.stringify({"password": datas.newPassword}),
                redirect: 'follow'
            };
            fetch("/api/profil/forgotMail", requestOptions)
                .then(response => {
                    if (response.status === 403) {
                        setParagraphContent("Le lien n'est plus valide")
                    }
                    if (response.status !== 200){
                        setParagraphContent("Le nouveau mot de passe doit contenir au moins une lettre minuscule, une lettre majuscule, un chiffre et doit être d'une longueur d'au moins 8 caractères. ")
                    }
                    else {
                        setShow(true)
                    }
                })
        }
    }

    const onClose = useCallback(() => {
        navigate("/");
    }, [navigate])

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)}>
                <TextField label="Nouveau mot de passe" type="password" variant="standard" className="textfield" {...register("newPassword")} required/><br/>
                <TextField label="Confirmer le nouveau mot de passe" type="password" variant="standard" className="textfield" {...register("newPasswordConfirmed")} required/><br/>
                <p className="text-danger w-100 h-auto">{paragraphContent}</p>
                <Buttons/>
            </form>

            <Modal show={show} size="lg" centered>
                <Modal.Header>
                    <Modal.Title>Modification mot de passe</Modal.Title>
                </Modal.Header>
                <Modal.Body>Votre mot de passe à été modifié, vous allez être redirigé vers la page d&apos;accueil</Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={onClose}>
                        Fermer
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}
export default ForgotPassword;