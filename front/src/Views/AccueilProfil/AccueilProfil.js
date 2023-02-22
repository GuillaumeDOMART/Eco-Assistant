import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useCallback, useEffect, useState} from "react";
import {Table} from "@mui/material";
import {Alert, Button, Container, Image, Placeholder, Row} from "react-bootstrap";
import logo from "../../Components/logo/Eco-Assistant_transparent.PNG";
import {useNavigate} from "react-router-dom";

/**
 * This function generate a line containing informations about a project
 *
 * @param data a json object of this type :
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
 */
function LigneTableauProjet(data){
    return (
        <tr className='table border-bottom border-2 border-secondary'>
            <td align={"center"} valign={"middle"}>{data.nomProjet}</td>
            <td align={"center"} valign={"middle"}>{data.etat}</td>
            <td align={"center"} valign={"middle"}><Button className="m-3" variant="secondary">Modifier</Button><Button className="m-3" variant="primary">Visionner</Button><Button className="m-3" variant="outline-primary">Exporter</Button><Button className="m-3" variant= "outline-danger">Dissocier</Button></td>
        </tr>
    );
}

/**
 * Generate a project listing table with data from API, use placeholder while loading
 */
function TableauProjets() {
    const [apiError, setApiError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const navigate = useNavigate()

    /**
     * Navigate to the page to begin the questionnaire
     */
    const handleBegin = useCallback(() => {
        navigate("/newproject")
    },[navigate])
    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        };
        fetch('/api/projet/user',options)
            .then(res => res.json())
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
    }, [])

    if (apiError) {
        return (
            <Alert variant="danger">
                <Alert.Heading >Error</Alert.Heading>
                {apiError.message}
            </Alert>
        );
    } else if (!isLoaded){
        return (
            <Table>
                <TableauProjetsHeader/>
                <LigneTableauProjetsPlaceholder/>
            </Table>
        );
    } else {
        if(items.length === 0){
            return (
                <>
                    <h1>Accueil</h1>
                    <br/>
                    <Row className="p-4 align-items-center">
                        <Container className= "w-50 h-50 align-items-center p-4">
                            <Image className="opacity-75 p-4 se" fluid src={logo} alt="logo eco-assistant" />
                            <p>Eco-Assistant est là pour toi. Calcule l&lsquo;empreinte carbone de l&lsquo;un de tes projets informatiques.</p>
                            <Button onClick={handleBegin} variant="secondary"> Commence le questionnaire ici !</Button>
                        </Container>
                    </Row>
                </>
            );
        }
        else {
            return (
                <>
                    <h1>Accueil</h1>
                    <br/>
                    <Table>
                        <TableauProjetsHeader/>
                        {items.map((item) => <LigneTableauProjet key={item.id} {...item}/>)}
                    </Table>
                </>
            );
        }
    }
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
                {items.map((item) => <LigneTableauProjet key={item.id} {...item}/>)}
            </Table>
        </>
    );
}

/**
 * Placeholder lines for project listing table
 */
function LigneTableauProjetsPlaceholder(){
    return(
        <tr className='table border-bottom border-3 border-primary'>
            <td> <Placeholder xs={5}/></td>
            <td><Placeholder xs={5}/></td>
            <td><Placeholder xs={2} aria-hidden="true"/></td>
        </tr>
    );
}

/**
 * Header for project listing table with data or placeholder
 */
function TableauProjetsHeader(){
    return (
        <tr className='table border-bottom border-3 border-primary'>
            <th>Nom du projet</th>
            <th>Etat du Projet</th>
            <th>Actions possibles</th>
        </tr>
    );
}


/**
 * Generate a web page containing a navigation bar and a project listing table
 */
function AccueilProfil() {
    const mockFront = false;
    let tableToDisplay = <MockTableauProjets/>;
    if(!mockFront) tableToDisplay = <TableauProjets/>

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">{tableToDisplay}</div>
        </div>
    );
}


export default AccueilProfil
