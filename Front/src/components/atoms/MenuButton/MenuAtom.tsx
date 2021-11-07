import React from "react";
import { css } from "@emotion/react";
import { NavLink } from "react-router-dom";

const menuAtomBtn = css`
	width: calc(11.111%);
	height: 40px;
	border-right: 2px solid #999;
	border-top: 2px solid #999;
	border-bottom: 2px solid #999;
	display: flex;
	align-items: center;
	justify-content: center;

	&:first-of-type {
		border-left: 2px solid #999;
	}
	&:hover {
		background-color: #54b689;
		color: #fff;
		font-weight: 700;
		font-size: 17px;
	}

	color: #333;
	font-size: 16px;
	font-weight: 500;
`;

type menuType = {
	to: string;
	menuName: string;
};

const MenuAtom: React.FC<menuType> = ({ to, menuName }) => {
	return (
		<NavLink
			activeStyle={{
				background: "#54b689",
				fontWeight: "bold",
				fontSize: "17px",
				color: "#fff",
			}}
			to={`/shopmenu/${to}`}
			css={menuAtomBtn}
		>
			{menuName}
		</NavLink>
	);
};

export default MenuAtom;
