/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Bar = styled.div`
	background-color: #93d1b5;
	margin-bottom: 3px;
	padding: 0.7em;
	padding-left: 2.7em;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	color: blue;
	color: #fff;
	font-size: 19px;
	font-weight: 600;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
`;

type Type = {
	children: React.ReactNode;
};

const Titlebar: React.FC<Type> = ({ children }) => {
	return <Bar>{children}</Bar>;
};
export default Titlebar;
