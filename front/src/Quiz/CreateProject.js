import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import {Col, Container, Row} from "react-bootstrap";

function CreateProject() {
    const navigate = useNavigate();
    const {register, handleSubmit} = useForm();

    const onSubmit = (datas) => {
        var myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("token")}`);
        myHeaders.append("Content-Type", "application/json");

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(datas),
            redirect: 'follow'
        };

        fetch("{{base_url}}/projet/create", requestOptions)
            .then(response => console.log(response.text()))
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
        navigate("/questionnaire")
    }

    return (
        <Container fluid>
            <Row className="border border-5 vh-100">
                <Col className="">
                    <h1>Nouveau Projet</h1>
                    <form onSubmit={handleSubmit(onSubmit)} className="">
                        <TextField label="Nom du projet" type="text" variant="standard" className="textfield" {...register("projectName")} required/><br/>
                        <Button type="submit">Créer le projet</Button><br/>
                    </form>
                </Col>
            </Row>
        </Container>
    );
}

export default CreateProject;