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
        <Col>
            <Logo/>
            <FormContainer/>
        </Col>

    )
}

/**
 * The component representing the logo of Eco Assistant
 * @returns {JSX.Element}
 * @constructor
 */
function Logo(){
    return (
        <Row>
            <img className="logo position-fixed start-0" src={require('../../Components/logo/logo.PNG')} alt={"logo"} style={{width:'10%',height: 'auto'}}/>
        </Row>
    );
}

/**
 * The component representing the form of connexion
 * @returns {JSX.Element}
 * @constructor
 */
function FormContainer(){
    return (
        <Row>
            <Container style={{
                position: 'absolute', left: '75%', top: '50%',
                transform: 'translate(-50%, -50%)',
            }}>

                <Col
                    md={6}
                    style={{
                        borderRadius: "5px",
                        border: "5px solid #aee1c6",
                        boxShadow: "15px 10px 1px #EAF7F0",
                        textAlign:"center",
                        padding:"5%"
                    }}>
                    <h1 style={{paddingBottom : "8%"}}>Mot de passe Oublié</h1>
                    <Form/>
                </Col>
            </Container>
        </Row>
    );
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
            setParagraphContent("Les mot de passe fournies ne corresponde pas")
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
                    } else {
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
                <Button href="/">Annuler</Button><Button type="submit">Valider</Button><br/>
                <p className="text-danger">{paragraphContent}</p>
            </form>

            <Modal show={show} size="lg" centered>
                <Modal.Header closeButton>
                    <Modal.Title>Modfication mot de passe</Modal.Title>
                </Modal.Header>
                <Modal.Body>Votre mot de passe à été modifié vous allez être redirigé vers la page d&apos;accueil</Modal.Body>
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