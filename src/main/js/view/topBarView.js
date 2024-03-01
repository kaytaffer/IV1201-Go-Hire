import React from "react";

export function TopBarView(props) {
    return (
        <div id={"top-bar"}>
            <div className={"left"}>
                <h1>Go Hire</h1>
            </div>
            <div className={"right"}>
                {props.username && <p>Logged in as: {props.username}</p>}
                {props.username && <button onClick={props.onLogout}>Logout</button>}
            </div>
        </div>
    )
}