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
function ForgotPasswordMail(){
    return (
        <>
            <Logo/>
            <Container className=" vh-100 vw-100 d-flex align-items-center">
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
        <Row>
            <img className="logo position-absolute start-0 top-0 m-1" src={require('../../Components/logo/logo.PNG')} alt={"logo"} style={{width:'10%',height: 'auto'}}/>
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
        <Container className= "d-flex align-items-center justify-content-center col-6 border border-5 border-secondary p-5 shadow-lg">
            <Col>
                <h1 style={{paddingBottom : "8%"}}>Mot de passe oublié?</h1>
                <Form/>
            </Col>
        </Container>
    );
}

/**
 * Validation buttons of form
 * @returns {JSX.Element}
 * @constructor
 */
function Buttons() {
    return(
        <Row>
            <Col>
                <Button href={"/connexion"} variant="outline-danger">Annuler</Button><br/>
            </Col>
            <Col>
                <Button type={"submit"} variant="outline-primary">Valider</Button><br/>
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
    const [paragraphContent, setParagraphContent] = useState("")
    const {register, handleSubmit} = useForm()
    const navigate = useNavigate();
    const [functiondDlay, setFunctionDelay] = useState(false);

    /**
     * Function for the form
     * @param datas data
     */
    const onSubmit = (datas) => {
        if(datas.mail !== datas.confirMail){
            setParagraphContent("Les mails fournis ne correspondent pas")
        }
        else if(!functiondDlay) {
            setFunctionDelay(true)
            const myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            const requestOptions = {
                method: 'PATCH',
                headers: myHeaders,
                body: JSON.stringify({"mail": datas.mail}),
                redirect: 'follow'
            };
            fetch("/api/auth/forgotMail", requestOptions)
                .then(response => {
                    setFunctionDelay(false)
                    if (response.status === 500) {
                        setParagraphContent("Problème rencontré pendant l'envoi du mail")
                    } else if (response.status === 404) {
                        setParagraphContent("Ce mail n'est pas utilisé pour un profil")
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
                <TextField label="Mail" type="email" variant="standard" className="textfield" {...register("mail")} required/><br/>
                <TextField label="Confirmer le mail" type="email" variant="standard" className="textfield" {...register("confirMail")} required/><br/>
                <p className="text-danger w-100 h-auto">{paragraphContent}</p>
                <Buttons/>
            </form>

            <Modal show={show} size="lg" centered>
                <Modal.Header>
                    <Modal.Title>Modification mot de passe</Modal.Title>
                </Modal.Header>
                <Modal.Body>Un mail pour modifier votre mot de passe vient d&apos;être envoyé</Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={onClose}>
                        Fermer
                    </Button>
                </Modal.Footer>
            </Modal>

        </>
    )
}
export default ForgotPasswordMail;