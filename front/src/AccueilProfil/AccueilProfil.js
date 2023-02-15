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
        <tr className='table border-bottom border-2 border-secondary'>
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
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ${token}'
            },
        };
        fetch('http://localhost/api/projet/user',options)
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
            <Table>
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
