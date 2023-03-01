import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import {Col, Container, Row} from "react-bootstrap";
import {useCallback, useState} from "react";


/**
 * Page for create project
 * @param onSubmit the function for the submission
 * @param register the function for the register
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function Project({onSubmit, register, fieldErrors}) {
    return (
        <Col className="">
            <h1>Nouveau Projet</h1>
            <form onSubmit={onSubmit} className="">
                <TextField label="Nom du projet" type="text" variant="standard"
                           className="textfield" {...register("nom")} required
                           error={!Boolean(fieldErrors.nom)}
                           helperText={fieldErrors.nom}/><br/>
                <Button type="submit">Créer le projet</Button><br/>
            </form>
        </Col>
    )
}

/**
 * Handle project submit
 * @param onSubmit onSubmit
 * @param handleSubmit handleSubmit
 * @returns {*} return the function
 */
function handleProjectSubmit(onSubmit, handleSubmit) {
    return handleSubmit(onSubmit);
}

/**
 * Page for create project
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function CreateProject() {
    const navigate = useNavigate();
    const {register, handleSubmit} = useForm();
    const [paragraphContent, setParagraphContent] = useState("")
    const [fieldErrors, setfieldErrors] = useState({})

    /**
     * Function for the submission of the data
     * @param datas the datas for the form
     * @returns {Promise<void>} the promise
     */
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
        if (response.status === 403) {
            navigate("/logout")
        }

        if (response.status >= 500) {
            setParagraphContent("Une erreur innatendue est survenue, veuillez réesayer plus tard");
            return;
        }

        const json = await response.json()
            .catch((_) => setParagraphContent("Une erreur innatendue est survenue, veuillez réesayer plus tard"));


        if (response.status >= 400 && json.fieldErrors) {
            setfieldErrors(json.fieldErrors);
            return;
        }

        sessionStorage.setItem("project", json.id)
        navigate("/questionnaire")
    }

    /**
     * function to handle quit
     */
    const handleQuit = useCallback(() => {
        if (sessionStorage.getItem("guest")) {
            navigate("/logout")
        } else {
            navigate("/profil")
        }
    }, [navigate]);

    return (
        <Container fluid>
            <Row className="border border-5 vh-100">
                <Col>
                    <Project onSubmit={handleProjectSubmit(onSubmit, handleSubmit)} register={register}
                             fieldErrors={fieldErrors}/>
                    <p className="text-danger w-100 h-auto">{paragraphContent}</p>
                    <Button onClick={handleQuit} type={"button"}>Revenir à l&lsquo;accueil</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default CreateProject;