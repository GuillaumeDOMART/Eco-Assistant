import React, {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import {ListGroup} from "react-bootstrap";
import LogoToHome from "../logo/LogoToHome";
/**
 * Composant qui représente la barre de navigation laérale.
 * */
function BarreNavCore() {
    return (
        <nav id="sidebarMenu" className="col-2 border border-2 border-secondary">
            <div className="position-sticky list-group list-group-flush px-3 pt-4 h-100">
                <BarNav_Content/>
            </div>
        </nav>
    );
}

/**
 * Return what is containted withing the nav bar
 */
function BarNav_Content(){
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

    const listeOnglets = [['Accueil', '/profil'], ['Profil', '/infoProfil'], ['Remplir un questionnaire', '/newproject'], ["Questions proposées", './']];
    if(isLoaded && datas.isAdmin) {
        listeOnglets.push(['Moderation', './'])
    }

    listeOnglets.push(['Se déconnecter', './logout'])

    const listeLiens = listeOnglets.map((x) => (
        <BarreNav_GroupItem key={x[0]} {...x}/>
    ));


    return(
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

function BarreNav_GroupItem(pair){
    const [isActive, setActive] = useState(false)
    const localisation = useLocation()
    console.log(localisation)

    useEffect(() => {
        if(pair[0] === localisation.pathname) {
        setActive(true)
        }
    });

    if(isActive) {
        return <ListGroup.Item variant="primary" action href={pair[1]}>{pair[0]}</ListGroup.Item>;
    } else return <ListGroup.Item action href={pair[1]}>{pair[0]}</ListGroup.Item>;
}

export default BarreNavCore;