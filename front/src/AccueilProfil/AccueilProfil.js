import BarreNavCore from "../BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {Alert, Table} from "@mui/material";
import Button from "@mui/material/Button";
import {Placeholder} from "react-bootstrap";

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
        <tr>
            <td>{data.nomProjet}</td>
            <td>{data.etat}</td>
            <td><Button >Modifier</Button><Button>Visionner</Button><Button>Exporter</Button><Button>Dissocier</Button></td>
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

    useEffect(() => {
        fetch('http://localhost/api/projets')
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
                <Alert.Heading>Error</Alert.Heading>
                <p>{apiError.message}</p>
            </Alert>
        );
    } else if (!isLoaded){
        return (
            <Table striped bordered hover>
                <TableauProjetsHeader/>
                <LigneTableauProjetsPlaceholder/>
            </Table>
        );
    } else {
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
        <tr>
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
        <tr>
            <th>Nom du projet</th>
            <th>Etat du questionnaire</th>
            <th>Actions possibles</th>
        </tr>
    );
}


/**
 * Generate a web page containing a navigation bar and a project listing table
 */
function AccueilProfil() {
    let mockFront = true;
    let tableToDisplay = <MockTableauProjets/>;
    if(!mockFront) tableToDisplay = <TableauProjets/>

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 px-5 pt-4">{tableToDisplay}</div>
        </div>
    );
}


export default AccueilProfil
