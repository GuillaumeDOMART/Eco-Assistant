import BarreNavCore from "../BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {Alert, Table} from "@mui/material";
import Button from "@mui/material/Button";
import {Placeholder} from "react-bootstrap";

function LigneTableauProjet(data){
    return (
        <tr>
            <td>{data["nomProjet"]}</td>
            <td>{data.etat}</td>
            <td><Button >Modifier</Button><Button>Visionner</Button><Button>Exporter</Button><Button>Dissocier</Button></td>
        </tr>
    );
}

function TableauProjets() {
    const [error, setError] = useState(null);
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
                    setError(error);
                }
            )
    }, [])

    if (error) {
        return (
            <Alert variant="danger">
                <Alert.Heading>Error</Alert.Heading>
                <p>{error.message}</p>
            </Alert>
        );
    } else if (!isLoaded) {
        return <TableauPlaceholder/>;
    } else {
        return (
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Nom du projet</th>
                    <th>Etat du questionnaire</th>
                    <th>Actions possibles</th>
                </tr>
                </thead>
                <tbody>
                {items.map(item => (
                    <LigneTableauProjet key={item['id']} {...item}/>
                ))}
                </tbody>
            </Table>
        );
    }
}

function TableauPlaceholder(){
    return(
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Nom du projet</th>
                    <th>Etat du questionnaire</th>
                    <th>Actions possibles</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td> <Placeholder xs={4}/></td>
                    <td><Placeholder xs={4}/></td>
                    <td><Placeholder xs={1} aria-hidden="true"/><Placeholder xs={1} aria-hidden="true"/><Placeholder
                    xs={1} aria-hidden="true"/><Placeholder xs={1} aria-hidden="true"/></td>
                </tr>
            </tbody>
        </Table>
        );
}


function AccueilProfil() {
        return (
            <div id="app" className="App .container-fluid row">
                <BarreNavCore/>
                <div className="col">
                    <TableauProjets/>
                </div>
            </div>
        );
    }


export default AccueilProfil
