import {Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import leaves from './leaves.png';
import "./Connection.css"

function Connection() {
    const {register, handleSubmit} = useForm();
    return (
           <Container className="" fluid>
               <Row className="vh-100">
                   <Col className="border border-danger mx-5 my-5">
                       <h1>Eco-Assistant</h1>
                       {/*<img className="leaves" src={require('./leaves.png')}  alt={"leaves"}/>*/}
                       <hr/>
                       <p>Envie de connaitre<br/>
                       l'empreinte carbone de<br/>
                       ton projet informatique ?<br/>
                           <br/>
                       Grâce au questionnaire<br/>
                       Eco-Assistant, calcule ton<br/>
                       impact environnemental :</p>
                       <a href="/questionnaire">Remplir le questionnaire*</a>
                       <hr/>
                       <p>* Remplir un questionnaire sans être connecté entrainera une perte<br/>
                       des données en cas d'abandon. Pour conserver l'avancement<br/>
                       connectez-vous ou créez un compte</p>
                   </Col>
                   <Col className="border border-danger mx-5 my-5">
                       <h1>Créer un compte</h1>
                       <form onSubmit={handleSubmit}>
                           <TextField label="Prénom" type="text" variant="standard" className="textfield"/><br/>
                           <TextField label="Nom" type="text" variant="standard" className="textfield"/><br/>
                           <TextField label="Adresse Mail" type="email" variant="standard" className="textfield"/><br/>
                           <TextField label="Mot de passe" type="password" variant="standard" className="textfield"/><br/>
                           <TextField label="Valider le mot de passe" type="password" variant="standard" className="textfield"/><br/>
                           <Button type={"submit"} style={{}}>Créer</Button><br/>
                           <p>Déjà un compte ? <a href="/questionnaire">Se connecter</a></p>
                       </form>
                   </Col>
               </Row>
           </Container>
    )
}

export default Connection