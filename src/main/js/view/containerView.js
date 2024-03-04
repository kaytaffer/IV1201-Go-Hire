import React from "react";

/**
 * Responsible for rendering the container around the entire page content.
 * @param props - props.
 * @param {JSX.Element} props.children - the content of the page.
 * @returns {JSX.Element} the rendered page.
 */
export function ContainerView(props) {
    return <div id={"page-container"}>{props.children}</div>
}