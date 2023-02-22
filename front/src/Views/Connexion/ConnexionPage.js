import {Col, Container, Row, Button} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {useState} from "react";





/**
 * The component representing the page for connexion
 * @returns {JSX.Element}
 * @constructor
 */
function ConnexionPage(){
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
        <>
            <img className="logo position-absolute top-0 start-0 m-1"  src={require('./logo.PNG')} alt={"logo"} style={{width:'10%',height: 'auto'}}/>
        </>
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
                <h1 style={{paddingBottom : "8%"}}>Page de Connexion</h1>
                <Form/>
            </Col>
        </Container>
    );
}

/**
 * The form of the page
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function Form(){
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
        if(reponse.status === 403){
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
            <TextField label="Adresse Mail" type="email" variant="standard" style={{width:"75%"}} required {...register('login')}/><br/>
            <TextField label="Mot de passe" type="password" variant="standard" style={{width:"75%"}} required {...register('password')}/><br/>
            <p className="text-danger w-100 h-auto">{paragraphContent}</p>
            <Row>
                <Col>
                    <Button href={"/"} variant="outline-danger">Retour</Button><br/>
                </Col>
                <Col>
                    <Button type={"submit"} variant="outline-primary">Connexion</Button><br/>
                </Col>
            </Row>

            <a href="/ForgotPasswordMail">Mot de passe oublié ?</a>
        </form>
    );
}
export default ConnexionPage;