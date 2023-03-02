import {Col, Container, Row, Button, Image} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import './ConnexionPage.css'


/**
 * The component representing the page for connexion
 * @returns {JSX.Element}
 * @constructor
 */
function ConnexionPage() {
    const navigate = useNavigate();

    useEffect(() => {
        const value = sessionStorage.getItem('token');
        if (value) {
            navigate("/profil")
        }
    }, [navigate]);

    /*<Logo/>*/
    return (
        <Container className="bg-page-as-paysage vh-100 vw-100 d-flex align-items-center" fluid>
            <Container className="bg-white bg-opacity-75 rounded col-6 p-5 shadow-lg">
                <FormContainer/>
            </Container>
        </Container>
    );
}

/**
 * The component representing the form of connexion
 * @returns {JSX.Element}
 * @constructor
 */
function FormContainer() {
    return (
        <>
            <HeaderAndLogo/>
            <Row>
                <Container fluid className="justify-content-center align-content-center">
                    <Form/>
                </Container>
            </Row>
        </>
    );
}

/**
 * Component containing header and logo
 */
function HeaderAndLogo() {
    return (
        <div className="d-flex flex-row justify-content-center align-items-center">
            <Image src={require("../../Components/logo/logo.PNG")} fluid className="col-3"/>
            <h1 className="align-self-center">Page de Connexion</h1>
        </div>
    );
}


/**
 * The form of the page
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function Form() {
    const {register, handleSubmit, reset} = useForm();
    const navigate = useNavigate();
    const [paragraphContent, setParagraphContent] = useState("")

    /**
     * Create the JSON for connexion and send it to the backend
     * @param datas
     */
    const onSubmit = async (datas) => {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(datas),
            redirect: 'follow'
        };

        const reponse = await fetch("/api/auth/authentication", requestOptions);
        if (reponse.status === 403) {
            setParagraphContent("Informations de connexion non valide, veuillez vérifier les informations renseignées")
            reset();
            return;
        }
        const json = await reponse.json();
        sessionStorage.setItem("token", json.token);
        navigate("/profil");
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <TextField className="w-75" label="Adresse Mail" type="email" variant="standard"
                       required {...register('login')}/><br/>
            <TextField className="w-75" label="Mot de passe" type="password" variant="standard"
                       required {...register('password')}/><br/>
            <p className="text-danger w-100 p-3">{paragraphContent}</p>
            <Row className="p-3">
                <Col>
                    <Button size="lg" href={"/"} variant="outline-danger">Retour</Button><br/>
                </Col>
                <Col/>
                <Col>
                    <Button size="lg" type={"submit"} variant="outline-success">Connexion</Button><br/>
                </Col>
            </Row>

            <a href="/ForgotPasswordMail">Mot de passe oublié ?</a>
        </form>
    );
}

export default ConnexionPage;