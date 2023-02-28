import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Alert, Button, Container, Image, Row} from "react-bootstrap";
import {Table} from "@mui/material";
import logo from "../../Components/logo/Eco-Assistant_transparent.PNG";

/**
 * Generate a web page containing a navigation bar and a project listing table
 */
function Moderation(){
    let tableToDisplay = <UsersList/>

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">{tableToDisplay}</div>
        </div>
    );
}
function UsersList(){
    const [items, setItems] = useState([]);
    const [apiError, setApiError] = useState(null);

    const navigate = useNavigate()
    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('/api/profil/users', options)
            .then(res => {
                if(res.status === 403){
                    navigate("/logout")
                }
                return res.json()
            })
            .then(
                (result) => {
                    setItems(result);
                },
                (error) => {
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
    } else {
            return (
                <>

                </>
            );

    }
}
export default Moderation