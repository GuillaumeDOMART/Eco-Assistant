import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import {Col, Container, Row} from "react-bootstrap";


function Project({onSubmit, register}) {
    return (
        <Col className="">
            <h1>Nouveau Projet</h1>
            <form onSubmit={onSubmit} className="">
                <TextField label="Nom du projet" type="text" variant="standard" className="textfield" {...register("nom")} required/><br/>
                <Button type="submit">Créer le projet</Button><br/>
            </form>
        </Col>
    )
}

function handleProjectSubmit(onSubmit, handleSubmit) {
    return handleSubmit(onSubmit);
}

function CreateProject() {
    const navigate = useNavigate();
    const {register, handleSubmit} = useForm();

    const onSubmit = async (datas) => {
        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("token")}`);
        myHeaders.append("Content-Type", "application/json");

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(datas),
            redirect: 'follow'
        };

        const response = await fetch("/api/projet/create", requestOptions)
        const json = await response.json();
        sessionStorage.setItem("project",json.id)
        navigate("/questionnaire")
    }

    const handleQuit = () => {
        navigate("/profil")
    }

    return (
        <Container fluid>
            <Row className="border border-5 vh-100">
                <Col>
                    <Project onSubmit={handleProjectSubmit(onSubmit, handleSubmit)} register={register}/>
                    <Button onClick={handleQuit} type={"button"}>Revenir à l&lsquo;accueil</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default CreateProject;