import React from "react";

export function HomePage(props){

    return <div>
        <h1> Home Page</h1>
        <p> Welcome {props.user.username}, with role {props.user.role}</p>
        </div>;

}