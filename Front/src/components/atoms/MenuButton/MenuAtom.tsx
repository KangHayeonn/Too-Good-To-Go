import React from "react";
import { css } from "@emotion/react";
import styled from "@emotion/styled";
import { NavLink } from "react-router-dom";

const menuAtomBtn = css`
	height: 40px;
	width: 116px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-collapse: collapse;

	box-shadow: 1.5px 0 0 0 #888, 0 1.5px 0 0 #888, 1.5px 1.5px 0 0 #888,
		1.5px 0 0 0 #888 inset, 0 1.5px 0 0 #888 inset;

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

type isBorderTop = {
	top?: boolean;
};

const Li = styled.li<isBorderTop>`
	display: flex;
	flex-direction: column;

	& > ul > li > a {
		border-top: none;
		width: 114px;
		padding-right: 2px;
	}
	& > ul > li {
		width: 100%;
	}

	&:hover {
		& > ul {
			display: block;
		}
	}
`;

type menuType = {
	to: string;
	menuName: string;
	children?: React.ReactNode;
	top?: boolean;
};

const MenuAtom: React.FC<menuType> = ({ top, to, menuName, children }) => {
	return (
		<Li top={top}>
			<NavLink
				activeStyle={{
					background: "#54b689",
					fontWeight: "bold",
					fontSize: "17px",
					color: "#fff",
				}}
				exact
				to={`/shopmenu/${to}`}
				css={menuAtomBtn}
			>
				{menuName}
			</NavLink>
			{children}
		</Li>
	);
};

MenuAtom.defaultProps = {
	children: null,
	top: false,
};

export default MenuAtom;
