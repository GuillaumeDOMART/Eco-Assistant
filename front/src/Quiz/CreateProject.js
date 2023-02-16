import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import {Col, Container, Row} from "react-bootstrap";


function Project(onSubmit, register) {
    return (
        <Col className="">
            <h1>Nouveau Projet</h1>
            <form onSubmit={onSubmit} className="">
                <TextField label="Nom du projet" type="text" variant="standard" className="textfield" {...register("projectName")} required/><br/>
                <Button type="submit">Créer le projet</Button><br/>
            </form>
        </Col>
    )
}
function CreateProject() {
    const navigate = useNavigate();
    const {register, handleSubmit} = useForm();

    const onSubmit = (datas) => {
        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("token")}`);
        myHeaders.append("Content-Type", "application/json");

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(datas),
            redirect: 'follow'
        };

        fetch("{{base_url}}/projet/create", requestOptions)
            .then(response => response.text())
        navigate("/questionnaire")
    }

    return (
        <Container fluid>
            <Row className="border border-5 vh-100">
                <Project onSubmit={handleSubmit(onSubmit)} register={register}/>
            </Row>
        </Container>
    );
}

export default CreateProject;