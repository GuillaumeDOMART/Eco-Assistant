import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useCallback, useEffect, useState} from "react";
import {RadioGroup, Table, TextField} from "@mui/material";
import {Alert, Button, Container, Image, Modal, Placeholder, Row} from "react-bootstrap";
import logo from "../../Components/logo/Eco-Assistant_transparent.PNG";
import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";

/**
 * Generate a project listing table with data from API, use placeholder while loading
 */
function TableauProjets() {
    const [apiError, setApiError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [showDissocier, setShowDissocier] = useState(false);
    const [showCopy, setShowCopy] = useState(false);
    const [selectedProject, setSelectedProject] = useState(null);
    const [fieldErrors, setFieldErrors] = useState({});
    const {register, reset, handleSubmit, formState: {errors}} = useForm();
    const [selectedValue, setSelectedValue] = useState("");

    const handleRadioChange = useCallback((event) => {
        setSelectedValue(event.target.value);
        console.log(event.target.value)
    }, [setSelectedValue]);


    const navigate = useNavigate()

    /**
     * Hide pop-up if deletion of profile is refused
     */
    const handleCancel = useCallback(() => {
        setSelectedProject(null);
        setShowDissocier(false);
        setShowCopy(false);
        setFieldErrors({});
    }, [setShowDissocier, setSelectedProject, setShowCopy])

    /**
     * Show the Dissocie pop-up when you push the button delete profil
     */
    const handleShowDissocier = useCallback((itemSelected) => {
        setSelectedProject(itemSelected)
        setShowDissocier(true);
    }, [setShowDissocier])

    /**
     * Show the copy pop-up when you push the button delete profil
     */
    const handleShowCopy = useCallback((itemSelected) => {
        if (showCopy) {
            setSelectedProject(null);
            setShowCopy(false);
        } else {
            setSelectedProject(itemSelected)
            setShowCopy(true);
        }
    }, [showCopy, setShowCopy])


    /**
     * Dissociate the project describe by the id
     * @type {(function(*=): void)|*}
     */
    const handleDissociate = useCallback(() => {
        const token = sessionStorage.getItem("token");
        const jsonBody = {id: selectedProject.id}
        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(jsonBody)
        };
        fetch('/api/projet/delete', options)
            .then(res => res.json())
            .then(() => {
                const copyItems = [...items];
                copyItems.splice(items.indexOf(selectedProject), 1);
                setItems(copyItems);
                setShowDissocier(false);
            })

    }, [setShowDissocier, items, setItems, selectedProject])

    const fetchCopy = useCallback(async (formData) => {
        if (formData.nom && formData.nom.length >= 50) {
            setFieldErrors({"nom": "Le nom du projet ne peut pas avoir un nom de plus de 50 caractères"})
            return;
        }

        if (formData.nom === selectedProject.nomProjet) {
            setFieldErrors({"nom": "Le nouveau projet ne peux avoir le même nom que le projet précédent"})
            return;
        }

        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${sessionStorage.getItem("token")}`);
        myHeaders.append("Content-Type", "application/json");

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify({id: selectedProject.id, projectName: formData.nom, projectType: formData.type}),
            redirect: 'follow'
        };

        const response = await fetch(`/api/projet/${selectedProject.id}/copy`, requestOptions);
        const json = await response.json();

        if (response.status >= 400 && json.fieldErrors) {
            setFieldErrors(json.fieldErrors);
            return;
        }

        // project has been copied and is in JSON
        const copyItems = [...items];
        copyItems.push(json)
        setItems(copyItems);
        setShowCopy(false);

        reset()
        setFieldErrors({})
        setSelectedValue("")

    }, [items, reset, selectedProject, setSelectedValue, setFieldErrors]);

    /**
     * Navigate to the page to begin the questionnaire
     */
    const handleBegin = useCallback(() => {
        navigate("/newproject")
    }, [navigate])


    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('/api/projet/user', options)
            .then(res => {
                if (res.status === 403) {
                    navigate("/logout")
                }
                return res.json()
            })
            .then(
                (result) => {
                    setIsLoaded(true);
                    setItems(result);
                },

                (error) => {
                    setIsLoaded(true);
                    setApiError(error);
                }
            )
    }, [navigate])

    const executeHandleDissociate = useCallback(() => {
        handleDissociate()
    }, [handleDissociate])

    if (apiError) {
        return (
            <Alert variant="danger">
                <Alert.Heading>Error</Alert.Heading>
                {apiError.message}
            </Alert>
        );
    } else if (!isLoaded) {
        return (
            <Table>
                <TableauProjetsHeader/>
                <LigneTableauProjetsPlaceholder/>
            </Table>
        );
    } else if (items.length === 0) {
        return (
            <>
                <h1>Accueil</h1>
                <br/>
                <Row className="p-4 align-items-center">
                    <Container className="w-50 h-50 align-items-center p-4">
                        <Image className="opacity-75 p-4 se" fluid src={logo} alt="logo eco-assistant"/>
                        <p>Eco-Assistant est là pour toi. Calcule l&lsquo;empreinte carbone de l&lsquo;un de tes
                            projets informatiques.</p>
                        <Button onClick={handleBegin} variant="secondary"> Commence le questionnaire ici !</Button>
                    </Container>
                </Row>
            </>
        );
    } else {
        return (
            <>
                <h1>Accueil</h1>
                <br/>
                <Table>
                    <TableauProjetsHeader/>
                    <tbody>
                    {items.map((item) => <LigneTableauProjet key={item.id} {...item}
                                                             itemsList={items}
                                                             itemSelected={item}
                                                             showDissocier={showDissocier}
                                                             handleShowDissocier={handleShowDissocier}
                                                             handleShowCopy={handleShowCopy}
                                                             handleCancel={handleCancel}
                                                             setItems={setItems}
                    />)}
                    </tbody>
                </Table>

                <Modal show={showCopy} onHide={handleCancel}>

                    <Modal.Header closeButton>
                        <Modal.Title>Copier le Projet</Modal.Title>
                    </Modal.Header>

                    <form onSubmit={handleSubmit(fetchCopy)}>
                        <Modal.Body>
                            <p>Veuillez donner un nom à la nouvelle copie du
                                projet &quot;{selectedProject !== null && selectedProject.nomProjet}&quot;</p>

                            <TextField label="Nom du projet" type="text" variant="standard"
                                       className="textfield" {...register("nom")} required
                                       error={Boolean(fieldErrors.nom)}
                                       helperText={fieldErrors.nom}/><br/>

                            <div id="demo-row-radio-buttons-group-label"> Type du projet</div>
                            <RadioGroup>
                                <div className="form-check form-check-inline">
                                    <label>
                                        <input
                                            type="radio"
                                            name="type"
                                            value="SIMULATION"
                                            checked={selectedValue === "SIMULATION"}
                                            className="form-check-input"
                                            onClick={handleRadioChange}
                                            {...register("type", {required: true})}
                                        />
                                        Simulation
                                    </label>
                                </div>
                                <div className="form-check form-check-inline">
                                    <label>
                                        <input
                                            type="radio"
                                            name="type"
                                            value="PROJET"
                                            checked={selectedValue === "PROJET"}
                                            className="form-check-input"
                                            onClick={handleRadioChange}
                                            {...register("type", {required: true})}
                                        />
                                        Projet
                                    </label>
                                </div>
                                {errors.type && (
                                    <div className="error-message" style={{fontSize: '14px', color: 'red'}}>
                                        Veuillez sélectionner un type de projet.</div>
                                )}
                            </RadioGroup>
                        </Modal.Body>

                        <Modal.Footer>
                            <Button variant="secondary" onClick={handleCancel}>Annuler</Button>
                            <Button type="submit">Copier le projet</Button><br/>
                        </Modal.Footer>
                    </form>
                </Modal>

                <Modal show={showDissocier} onHide={handleCancel}>
                    <Modal.Header closeButton>
                        <Modal.Title>Dissocier le Projet</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Êtes-vous sûr de vouloir dissocier ce projet ?</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCancel}>
                            Annuler
                        </Button>
                        <Button variant="outline-danger" onClick={executeHandleDissociate}>
                            Supprimer
                        </Button>
                    </Modal.Footer>
                </Modal>
            </>
        );
    }
}


/**
 * Buttons container adapted to the state of the project
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function ButtonSet(datas) {
    return (
        <>
            {datas.etat === "INPROGRESS" &&
                <Button className="m-3" variant="secondary" onClick={datas.handleClickModifyButton}>Modifier</Button>}

            {datas.etat === "FINISH" &&
                <Button className="m-3" variant="primary" href={`/result?id=${datas.idProject}`}>Visionner</Button>}

            <Button className="m-3" variant="outline-primary" onClick={datas.handleShowCopy}>Créer une copie</Button>
            <Button className="m-3" variant="outline-danger" onClick={datas.handleShowDissociate}>Dissocier</Button>
        </>
    );
}

/**
 * This function generate a line containing informations about a project
 *
 *
 ```json
 {
        "id": 1,
        "profil": {
            "id": 2,
            "mail": "createur-dev@demo.fr",
            "nom": "DEMO",
            "prenom": "Createur Dev",
            "admin": false
        },
        "nomProjet": "QUESTIONAIRE POUR DEVELOPPEURS",
        "etat": "INPROGRESS"
    }
 ```
 * @param datas
 */
function LigneTableauProjet(datas) {
    const navigate = useNavigate();

    const handleClick = React.useCallback(() => {
        sessionStorage.setItem("project", datas.id)
        navigate("/questionnaire")
    }, [navigate, datas.id])

    const executeHandleShowCopy = useCallback(() => {
        datas.handleShowCopy(datas.itemSelected)
    }, [datas])

    const executeHandleShowDissociate = useCallback(() => {
        datas.handleShowDissocier(datas.itemSelected)
    }, [datas])

    return (
        <>
            <tr className='table border-bottom border-2 border-secondary'>
                <td align={"center"} valign={"middle"}>{datas.nomProjet}</td>
                <td align={"center"} valign={"middle"}>{datas.etat}</td>
                <td align={"center"} valign={"middle"}>{datas.type}</td>
                <td align={"center"} valign={"middle"}>
                    <ButtonSet idProject={datas.id}
                               etat={datas.etat}
                               handleClickModifyButton={handleClick}
                               handleShowDissociate={executeHandleShowDissociate}
                               handleShowCopy={executeHandleShowCopy}
                    />
                </td>
            </tr>
        </>
    );
}

/**
 * Placeholder lines for project listing table
 */
function LigneTableauProjetsPlaceholder() {
    return (
        <tbody>
        <tr className='table border-bottom border-3 border-primary'>
            <td><Placeholder xs={5}/></td>
            <td><Placeholder xs={5}/></td>
            <td><Placeholder xs={2} aria-hidden="true"/></td>
        </tr>
        </tbody>
    );
}

/**
 * Header for project listing table with data or placeholder
 */
function TableauProjetsHeader() {
    return (
        <thead>
        <tr className='table border-bottom border-3 border-primary'>
            <th>Nom du projet</th>
            <th>Etat du Projet</th>
            <th>Type de Projet</th>
            <th>Actions possibles</th>
        </tr>
        </thead>
    );
}


/**
 * Generate a web page containing a navigation bar and a project listing table
 */
function AccueilProfil() {
    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">
                <TableauProjets/>
            </div>
        </div>
    );
}

export default AccueilProfil
