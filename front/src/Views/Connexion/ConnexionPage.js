import {Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";





/**
 * The component representing the page for connexion
 * @returns {JSX.Element}
 * @constructor
 */
function ConnexionPage(){


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
            <img className="logo position-fixed start-0" src={require('./logo.PNG')} alt={"logo"} style={{width:'10%',height: 'auto'}}/>
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
                    <h1 style={{paddingBottom : "8%"}}>Page de Connexion</h1>
                    <Form/>
                </Col>
            </Container>
        </Row>
    );
}
function Form(){
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();

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
        const json = await reponse.json();
        sessionStorage.setItem("token", json.token);
        navigate("/profil");
    }

return (
    <form onSubmit={handleSubmit(onSubmit)}>
        <TextField label="Adresse Mail" type="email" variant="standard" style={{width:"75%"}} required {...register('login', {
            required: 'Email is required',
            pattern: {
                value: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
                message: 'Please enter a valid email',
            },
        })}/><br/>
        <TextField label="Mot de passe" type="password" variant="standard" style={{width:"75%"}} required {...register('password')}/><br/>
        <Row>
            <Col>
                <Button href={"/"} style={{}}>Retour</Button><br/>
            </Col>
            <Col>
                <Button type={"submit"} style={{}}>Connexion</Button><br/>
            </Col>
        </Row>

        <a href="/forgotPassword">Mot de passe oublié ?</a>
    </form>
);
}
export default ConnexionPage;