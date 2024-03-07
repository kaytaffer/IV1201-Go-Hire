/*
Pre-defined error objects with their error type matched with the message to be displayed to user.
 */

export const APPLICATION_ALREADY_HANDLED = {errorType: "APPLICATION_ALREADY_HANDLED", message: "application-already-handled"}
export const INSUFFICIENT_CREDENTIALS = {errorType: "INSUFFICIENT_CREDENTIALS", message: "no-access-rights"}
export const LOGIN_FAIL = {errorType: "LOGIN_FAIL", message: "credentials-do-not-match"}
export const SERVER_INTERNAL = {errorType: "SERVER_INTERNAL", message: "something-went-wrong"}
export const USER_INPUT_ERROR = {errorType: "USER_INPUT_ERROR", message: "wrong-input"}
export const USERNAME_ALREADY_EXISTS = {errorType: "USERNAME_ALREADY_EXISTS", message: "username-exists"}
export const PAGE_DOES_NOT_EXIST = {errorType: "PAGE_DOES_NOT_EXIST", message: "page-does-not-exist"}
export const AUTHENTICATION_FAIL = {errorType: "AUTHENTICATION_FAIL", message: "credentials-do-not-match"}