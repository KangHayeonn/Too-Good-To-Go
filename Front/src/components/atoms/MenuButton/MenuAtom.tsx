import React from "react";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";

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

	p {
		color: #333;
		font-size: 15px;
		font-weight: 400;
	}
`;

type menuType = {
	to: string;
	menuName: string;
};

const MenuAtom: React.FC<menuType> = ({ to, menuName }) => {
	return (
		<Link to={`shopmenu/${to}`} css={menuAtomBtn}>
			<p>{menuName}</p>
		</Link>
	);
};

export default MenuAtom;
