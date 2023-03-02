import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useCallback, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Alert, Button, Container, Modal, Row, Table} from "react-bootstrap";


/**
 * Generate a web page containing a navigation bar and a project listing table
 */
function Moderation(){

    return (
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <Container className="col-10 p-5 h-100">
                <ListContainer/>
            </Container>
        </div>
    );
}
function ListContainer(){

    return (
        <>
            <h1>Modération</h1>
            <br/>
            <Container className="h-25 w-100">
                <UsersList/>
            </Container>
            <Container className="p-5 h-25 w-100"/>

        </>
    )
}
const filterItems = (items,query)=>{
    return items.filter((item)=>{
        const itemName = item.nom+" "+item.prenom
        const lowerCaseName = itemName.toLowerCase()
        return lowerCaseName.includes(query.toLowerCase())
    })
}
/**
 * Header for users listing table with data or placeholder
 */
function TableauUsersHeader() {
    return (
        <thead>
        <tr className='table border-bottom border-3 border-primary'>
            <th>Nom de l'utilisateur</th>
            <th>Mail</th>
        </tr>
        </thead>
    );
}
function LigneTableauUsers(datas) {
    const navigate = useNavigate();

    /**
     * Hide pop-up if deletion of user is refused
     */
    const handleCancel = useCallback(() => {
        datas.setDeletedUser(null)
        datas.handleShowDeleteUser(false);
    },[datas])

    /**
     * Show the pop-up when you push the button delete user
     */
    const handleShow = useCallback((itemSelected) => {
        datas.setDeletedUser(itemSelected)
        datas.handleShowDeleteUser(true);
    },[datas])

    const handleClick = useCallback(() => {
        sessionStorage.setItem("user",datas.id)

    }, [datas.id])
    const executeHandleShow = useCallback( ()=>{
        handleShow(datas.itemSelected)
    },[datas,handleShow])

    return (
        <>
            <tr className='table border-bottom border-2 border-secondary'>
                <td align={"center"} valign={"middle"}>{datas.nom+" "+datas.prenom}</td>
                <td align={"center"} valign={"middle"}>{datas.mail}</td>
                <td align={"center"} valign={"middle"}>
                    <Button className="m-3" variant="outline-danger" onClick={executeHandleShow}>Supprimer</Button>
                </td>
            </tr>
            <Modal show={datas.showDeleteUser} onHide={handleCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Supprimer l'utilisateur</Modal.Title>
                </Modal.Header>
                <Modal.Body>Es-tu sûr de vouloir supprimer cet utilisateur ?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCancel}>
                        Annuler
                    </Button>
                    <Button variant="outline-danger" onClick={datas.handleDeleteUser}>
                        Supprimer
                    </Button>
                </Modal.Footer>
            </Modal>
        </>


    );
}
function UsersList(){
    const [items, setItems] = useState([]);
    const { search } = window.location;
    const query = new URLSearchParams(search).get('s');
    const [apiError, setApiError] = useState(null);
    const [deletedUser,setDeletedUser] = useState(null);
    const [showDeleteUser, setShowDeleteUser] = useState(false);
    const [searchQuery, setSearchQuery] = useState(query || '')
    const handleSearchChange = useCallback((event)=>{
        setSearchQuery(event.target.value)
    },[])
    const filteredItems = filterItems(items, searchQuery);

    /**
     * Dissociate the project describe by the id
     * @type {(function(*=): void)|*}
     */
    const handleDeleteUser= useCallback(()=>{
        const token = sessionStorage.getItem("token");
        const jsonBody = {idToBan : deletedUser.id}
        const options = {
            method: 'PATCH',
            headers: {
                'Content-Type' : 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(jsonBody)
        };
        fetch('/api/admin/ban', options)
            .then(res => res.json())
            .then(()=>{
                const copyItems = [...filteredItems];
                copyItems.splice(filteredItems.indexOf(deletedUser), 1);
                setItems(copyItems);
                setShowDeleteUser(false);
            })


    },[setShowDeleteUser,deletedUser])
    const navigate = useNavigate()
    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            redirect: 'follow'
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
                <SearchBar searchQuery={searchQuery} onChange={handleSearchChange}/>
                <Container className="w-100 h-100 navbar-nav-scroll">
                    <Table>
                        <TableauUsersHeader/>
                        <tbody>
                        {filteredItems.map((item) => (
                            <LigneTableauUsers key={item.id} {...item} itemSelected={item}  itemsList={filteredItems} showDeleteUser={showDeleteUser} handleShowDeleteUser={setShowDeleteUser} setDeletedUser={setDeletedUser} setItems={setItems} handleDeleteUser={handleDeleteUser}>{item.nom}</LigneTableauUsers>
                        ))}
                        </tbody>
                    </Table>
                </Container>
            </>
        );

    }
}
function SearchBar(datas) {
    return(
        <>
            <label htmlFor="header-search">
                <span className="visually-hidden">Recherchez des utilisateurs</span>
            </label>
            <input
                value={datas.searchQuery}
                onChange={datas.onChange}
                type="text"
                id="header-search"
                placeholder="Rechercher des utilisateurs"
            />
        </>
    );
}

export default Moderation