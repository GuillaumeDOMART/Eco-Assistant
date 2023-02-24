import React, {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import {Alert, ListGroup, Placeholder} from "react-bootstrap";
import LogoToHome from "../logo/LogoToHome";

/**
 * Composant qui représente la barre de navigation laérale.
 * */
function BarreNavCore() {
    return (
        <nav id="sidebarMenu" className="col-2 border-end border-3 border-secondary">
            <div className="position-sticky list-group list-group-flush px-3 pt-4 h-100">
                <BarNavContent/>
            </div>
            <div className="col-2 fixed-bottom mb-2">
                v1.0-beta
            </div>
        </nav>
    );
}

/**
 * Return what is containted withing the nav bar
 */
function BarNavContent() {
    const [isLoaded, setIsLoaded] = useState(false);
    const [apiError, setApiError] = useState(null);
    const [datas, setDatas] = useState([]);

    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`

            }
        };
        fetch(`/api/profil/user`, options)
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setDatas(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setApiError(error);
                }
            )
    }, [])

    if (apiError) {
        return (
            <>
                <LogoToHome/>
                <Alert variant="danger">
                    <Alert.Heading>Error API</Alert.Heading>
                    {apiError.message}
                </Alert>
            </>
        );
    }

    const listeOnglets = [['Accueil', '/profil'], ['Profil', '/infoProfil'], ['Remplir un questionnaire', '/newproject'], ["Questions proposées", './']];
    if (isLoaded && datas.isAdmin) {
        listeOnglets.push(['Moderation', './'])
    }
    listeOnglets.push(['Se déconnecter', './logout'])


    let listeLiens = listeOnglets.map(x => (
        <BarreNavGroupItemPlaceholder key={x[0]}/>
    ));

    if (isLoaded) {
        listeLiens = listeOnglets.map((x) => (
            <BarreNavGroupItem key={x[0]} {...x}/>
        ));
    }

    return (
        <>
            <LogoToHome/>
            <ListGroup variant="flush">
                {listeLiens}
            </ListGroup>
        </>
    );
}

/**
 * Return a list group item, with the correct color
 */
function BarreNavGroupItem(pair) {
    const [isActive, setActive] = useState(false)
    const localisation = useLocation()

    useEffect(() => {
        if (pair[0] === localisation.pathname) {
            setActive(true)
        }
    }, [pair, localisation]);

    if (isActive) {
        return <ListGroup.Item variant="primary" action href={pair[1]}>{pair[0]}</ListGroup.Item>;
    } else return <ListGroup.Item action href={pair[1]}>{pair[0]}</ListGroup.Item>;
}

/**
 * Return a placeholder for Group Item
 */
function BarreNavGroupItemPlaceholder() {
    return <Placeholder variant="light" className="m-2" aria-hidden="true" as={ListGroup.Item}/>;
}

export default BarreNavCore;