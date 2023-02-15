import {Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import logo from "./logo.PNG"
function ConnexionPage(){
    const {register, handleSubmit} = useForm();
    return (
        <>
            <Col >
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
                            <form onSubmit={handleSubmit}>
                                <TextField label="Adresse Mail" type="email" variant="standard" style={{width:"75%"}}/><br/>
                                <TextField label="Mot de passe" type="password" variant="standard" style={{width:"75%"}}/><br/>
                                <Row>
                                    <Col>
                                        <Button type={"submit"} style={{}}>Retour</Button><br/>
                                    </Col>
                                    <Col>
                                        <Button type={"submit"} style={{}}>Connexion</Button><br/>
                                    </Col>
                                </Row>

                                <p>Mot de passe oublié ? <a href="/questionnaire">Réinitialiser le mot de passe</a></p>
                            </form>
                        </Col>
                </Container>
            </Row>
            </Col>

        </>
    )
}
export default ConnexionPage;