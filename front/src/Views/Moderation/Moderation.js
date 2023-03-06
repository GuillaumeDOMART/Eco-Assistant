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

/**
 * Component that holds lists
 * @returns {JSX.Element}
 * @constructor
 */
function ListContainer(){
    const [selectedUser,setSelectedUser] = useState(null);
    const [showSelectedUserProjects,setShowSelectedUserProjects] = useState(false);
    const [projectsOfUser,setProjectsOfUser] = useState([]);
    return (
        <>
            <h1>Modération</h1>
            <br/>
            <Container className="h-25 w-100">
                <UsersList selectedUser={selectedUser} setSelectedUser={setSelectedUser} setProjectsOfUser={setProjectsOfUser} setShowSelectedUserProjects={setShowSelectedUserProjects}/>
            </Container>
            <Row className="h-25"></Row>
            <Container className="h-25 w-100">
                <ProjectsList selectedUser={selectedUser} projectsOfUser={projectsOfUser} setProjectsOfUser={setProjectsOfUser} showSelectedUserProjects={showSelectedUserProjects} />
            </Container>

        </>
    )
}

/**
 * Component that holds projectsList
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function ProjectsList(props){
    const [deletedProject,setDeletedProject] = useState(null);
    const [showDeleteProject, setShowDeleteProject] = useState(false);


    /**
     * Delete the user describe by the id
     * @type {(function(*=): void)|*}
     */
    const handleDeleteProject= useCallback(()=>{
        const token = sessionStorage.getItem("token");
        const jsonBody = {idProjectToBan : deletedProject.id}
        const options = {
            method: 'PATCH',
            headers: {
                'Content-Type' : 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(jsonBody)
        };
        fetch('/api/admin/banProject', options)
            .then(res => res.json())
            .then(()=>{
                const copyItems = [...props.projectsOfUser];
                copyItems.splice(props.projectsOfUser.indexOf(deletedProject), 1);
                props.setProjectsOfUser(copyItems);
                setShowDeleteProject(false);
            })


    },[setShowDeleteProject,deletedProject,props])

    if (!props.showSelectedUserProjects){
        return (
            <>
            </>
        );
    }
    else {
        return (
            <>
                <Container className="w-100 h-100 navbar-nav-scroll">
                    <Table>
                        <TableauProjectsHeader/>
                        <tbody>
                        {props.projectsOfUser.map((item) => (
                            <LigneTableauProjects key={item.id} {...item}
                                                  project={item}
                                                  itemsList={props.projectsOfUser}
                                                  showDeleteProjects={showDeleteProject}
                                                  handleShowDeleteProject={setShowDeleteProject}
                                                  setDeletedProject={setDeletedProject}
                                                  handleDeleteProject={handleDeleteProject}
                                                  handleProjectsOfUser={props.setProjectsOfUser}>{item.nom}</LigneTableauProjects>
                        ))}
                        </tbody>
                    </Table>
                </Container>
            </>
        );
    }

}

/**
 * Function that returns filtered items.
 * @param items
 * @param query
 * @returns {*}
 */
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
    const handleShowDeletePopup = useCallback(() => {
        datas.setDeletedUser(datas.itemSelected)
        datas.handleShowDeleteUser(true);
    },[datas])

    const handleShowSelectedUserProjects = useCallback(()=>{
        const token = sessionStorage.getItem("token");
        const options = {
            method: 'GET',
            headers: {
                'Content-Type' : 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };
        fetch('/api/admin/projects/user/'+datas.id, options)
            .then(res => res.json())
            .then((res)=>{
                datas.setProjectsOfUser(res)
                datas.setSelectedUser(datas.id)
                datas.handleShowSelectedUserProjects(true);
            })

    },[datas])
    /*const handleClick = useCallback(() => {
        sessionStorage.setItem("user",datas.id)

    }, [datas.id])*/


    return (
        <>
            <tr className='table border-bottom border-2 border-secondary'>
                <td align={"center"} valign={"middle"}>{datas.lastName+" "+datas.firstName}</td>
                <td align={"center"} valign={"middle"}>{datas.mail}</td>
                <td align={"center"} valign={"middle"}>
                    <Button className="m-3" variant="secondary" onClick={handleShowSelectedUserProjects}>Visualiser ces projets</Button>
                    <Button className="m-3" variant="outline-danger" onClick={handleShowDeletePopup}>Supprimer</Button>
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

/**
 * Header for projects listing table with data or placeholder
 */
function TableauProjectsHeader() {
    return (
        <thead>
        <tr className='table border-bottom border-3 border-primary'>
            <th>Nom du projet</th>
            <th>Mail du propriétaire</th>
        </tr>
        </thead>
    );
}

/**
 * Component that represent Line of TableauProjects
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function LigneTableauProjects(datas) {

    const navigate = useNavigate()



    /**
     * Hide pop-up if deletion of user is refused
     */
    const handleCancel = useCallback(() => {
        datas.setDeletedProject(null)
        datas.handleShowDeleteProject(false);
    },[datas])

    /**
     * Show the pop-up when you push the button delete user
     */
    const handleShowDeletePopup = useCallback(() => {
        datas.setDeletedProject(datas.project)
        datas.handleShowDeleteProject(true);
    },[datas])

    /**
     * Show the result of a user
     */
    const handleShowResult = useCallback(() => {
        navigate('/result?id='+datas.project.id)
    },[datas,navigate])

    return (
        <>
            <tr className='table border-bottom border-2 border-secondary'>
                <td align={"center"} valign={"middle"}>{datas.project.nomProjet}</td>
                <td align={"center"} valign={"middle"}>{datas.project.profil.mail}</td>
                <td align={"center"} valign={"middle"}>
                    <Button className="m-3" variant="outline-primary" onClick={handleShowResult}>Visionner</Button>
                    <Button className="m-3" variant="outline-danger" onClick={handleShowDeletePopup}>Supprimer</Button>
                </td>
            </tr>
            <Modal show={datas.showDeleteProjects} onHide={handleCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Supprimer l'utilisateur</Modal.Title>
                </Modal.Header>
                <Modal.Body>Es-tu sûr de vouloir supprimer cet utilisateur ?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCancel}>
                        Annuler
                    </Button>
                    <Button variant="outline-danger" onClick={datas.handleDeleteProject}>
                        Supprimer
                    </Button>
                </Modal.Footer>
            </Modal>
        </>


    );
}

/**
 * Component that hold UsersList
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
function UsersList(props){
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


    },[setShowDeleteUser,deletedUser,filteredItems])
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
                            <LigneTableauUsers key={item.id}
                                               {...item}
                                               itemSelected={item}
                                               itemsList={filteredItems}
                                               setProjectsOfUser={props.setProjectsOfUser}
                                               showDeleteUser={showDeleteUser}
                                               handleShowDeleteUser={setShowDeleteUser}
                                               setDeletedUser={setDeletedUser}
                                               setItems={setItems}
                                               handleDeleteUser={handleDeleteUser}
                                               setSelectedUser={props.setSelectedUser}
                                               handleShowSelectedUserProjects={props.setShowSelectedUserProjects}
                            >{item.nom}</LigneTableauUsers>
                        ))}
                        </tbody>
                    </Table>
                </Container>
            </>
        );

    }
}

/**
 * Component that represents searchBar.
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
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