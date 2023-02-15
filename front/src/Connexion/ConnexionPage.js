import {Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import logo from "./logo.PNG"
function ConnexionPage(){
    const {register, handleSubmit} = useForm();
    const onSubmit = (datas)=>{
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(datas),
            redirect: 'follow'
        };

        fetch("{{base_url}}/auth/authentication", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }
    return (
        <>
            <Container>
            <Col>
            <Row>
                <Container>
                <img className="logo position-fixed start-0" src={require('./logo.PNG')}  alt={"logo"} style={{width:'10%',height: 'auto'}}/>
                </Container>
            </Row>
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

                                <p>Mot de passe oublié ? <a href="/forgotPassword">Réinitialiser le mot de passe</a></p>
                            </form>
                        </Col>
                </Container>
            </Row>
            </Col>
            </Container>

        </>
    )
}
export default ConnexionPage;