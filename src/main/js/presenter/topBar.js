import React from "react";
import {TopBarView} from "../view/topBarView";

export function TopBar(props) {
    return <TopBarView user={props.user}/>
}