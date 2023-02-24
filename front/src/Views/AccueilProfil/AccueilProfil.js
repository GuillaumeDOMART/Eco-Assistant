import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useCallback, useEffect, useState} from "react";
import {Table} from "@mui/material";
import {Alert, Button, Container, Image, Modal, Placeholder, Row} from "react-bootstrap";
import logo from "../../Components/logo/Eco-Assistant_transparent.PNG";
import {useNavigate} from "react-router-dom";



/**
 * Generate a project listing table with data from API, use placeholder while loading
 */
function TableauProjets() {
    const [apiError, setApiError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [show, setShow] = useState(false);
    const [deletedProject,setDeletedProject] = useState(null);
    const navigate = useNavigate()

    /**
     * Hide pop-up if deletion of profile is refused
     */
    const handleCancel = useCallback(() => {
        setDeletedProject(null)
        setShow(false);
    },[setShow,setDeletedProject])

    /**
     * Show the pop-up when you push the button delete profil
     */
    const handleShow = useCallback((itemSelected) => {
        setDeletedProject(itemSelected)
        setShow(true);
    },[setShow])

    /**
     * Dissociate the project describe by the id
     * @type {(function(*=): void)|*}
     */
    const handleDissociate = useCallback((itemsList)=>{
        const token = sessionStorage.getItem("token");
        const jsonBody = {id : deletedProject.id}
        const options = {
            method: 'PUT',
            headers: {
                'Content-Type' : 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(jsonBody)
        };
        fetch('/api/projet/delete', options)
            .then(res => res.json())
            .then(()=>{
                const copyItems = [...itemsList];
                copyItems.splice(itemsList.indexOf(deletedProject), 1);
                setItems(copyItems);
                setShow(false);
            })


    },[setShow,setItems,deletedProject])

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
                if(res.status === 403){
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
    } else {
        if (items.length === 0) {
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
                            {items.map((item) => <LigneTableauProjet key={item.id} {...item} itemsList={items} itemSelected={item} showVar={show} handleShow={handleShow} handleCancel={handleCancel} handleDissociate={handleDissociate} />)}
                        </tbody>
                    </Table>
                </>
            );
        }
    }
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
        sessionStorage.setItem("project",datas.id)
        navigate("/questionnaire")

    }, [navigate, datas.id])


    const executeHandleShow = useCallback( ()=>{
        datas.handleShow(datas.itemSelected)
    },[datas])
    const executeHandleDissociate = useCallback(()=>{
        datas.handleDissociate(datas.itemsList)
    },[datas])



    return (
        <>
            <tr className='table border-bottom border-2 border-secondary'>
                <td align={"center"} valign={"middle"}>{datas.nomProjet}</td>
                <td align={"center"} valign={"middle"}>{datas.etat}</td>
                <td align={"center"} valign={"middle"}>
                    <Button className="m-3" variant="secondary" onClick={handleClick}>Modifier</Button>
                    <Button className="m-3" variant="primary" href={`/result?id=${datas.id}`}>Visionner</Button>
                    <Button className="m-3" variant="outline-primary">Créer une copie</Button>
                    <Button className="m-3" variant="outline-danger" onClick={executeHandleShow}>Dissocier</Button>
                </td>
            </tr>
            <Modal show={datas.showVar} onHide={datas.handleCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Dissocier le Projet</Modal.Title>
                </Modal.Header>
                <Modal.Body>Es-tu sûr de vouloir dissocier ce projet ?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={datas.handleCancel}>
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
/**
 * Generate a project listing table with Mock data
 */
function MockTableauProjets() {
    const items = [
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
        },
        {
            "id": 2,
            "profil": {
                "id": 3,
                "mail": "createur-support@demo.fr",
                "nom": "DEMO",
                "prenom": "Createur Support",
                "admin": false
            },
            "nomProjet": "QUESTIONAIRE POUR L EQUIPE SUPPORT",
            "etat": "INPROGRESS"
        }
    ]

    return (
        <>
            <h1>Accueil (TEST FRONT)</h1>
            <br/>

            <Table>
                <TableauProjetsHeader/>
                <tbody>
                {items.map((item) => <LigneTableauProjet key={item.id} {...item}/>)}
                </tbody>
            </Table>
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
            <th>Actions possibles</th>
        </tr>
        </thead>
    );
}


/**
 * Generate a web page containing a navigation bar and a project listing table
 */
function AccueilProfil() {
    const mockFront = false;
    let tableToDisplay = <MockTableauProjets/>;
    if (!mockFront) tableToDisplay = <TableauProjets/>

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">{tableToDisplay}</div>
        </div>
    );
}

export default AccueilProfil
