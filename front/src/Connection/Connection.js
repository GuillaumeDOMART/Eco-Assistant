import {Button, Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Input} from "@mui/material";

function Connection() {
    const {register, handleSubmit} = useForm();
    return (
       <>
           <Container>
               <Row>
                   <Col
                       style={{backgroundColor:"green", margin:"10px"}}>
                       <p>Test</p>
                   </Col>
                   <Col
                       style={{backgroundColor:"pink", margin:"10px"}}>
                       <h1>Créer un compte</h1>
                       <form onSubmit={handleSubmit}>
                            <Input type={"text"} placeholder={"Prénom"}/><br/>
                           <Input type={"text"} placeholder={"Nom"}/><br/>
                           <Input type={"text"} placeholder={"Adresse Mail"}/><br/>
                           <Input type={"text"} placeholder={"Mot de passe"}/><br/>
                           <Input type={"text"} placeholder={"Valider le mot de passe"}/><br/>
                           <Button type={"submit"} style={{}}>Créer</Button>
                       </form>
                   </Col>
               </Row>
           </Container>
       </>
    )
}

export default Connection