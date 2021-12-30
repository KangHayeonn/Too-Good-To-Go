import React from "react";
import { css } from "@emotion/react";
import styled from "@emotion/styled";
import { NavLink } from "react-router-dom";

const menuAtomBtn = css`
	width: 80px;
	height: calc(100%);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	border-collapse: collapse;
	background-color: #ddd;
	color: #999;
	font-size: 16px;
	font-weight: 400;
	& > p {
		margin-top: 5px;
		font-weight: bold;
	}
`;

const Li = styled.li`
	display: flex;
	height: calc((100% - 2px) / 3);
	background-color: #ddd;
	border-top: 1px solid #999;

	&:first-child {
		border-top: 0;
	}
`;

type menuType = {
	to: string;
	menuName: string;
	orderAmount: number;
};

const ManagerOrderBtnAtom: React.FC<menuType> = ({
	to,
	menuName,
	orderAmount,
}) => {
	return (
		<Li>
			<NavLink
				activeStyle={{
					background: "#fff",
					color: "#333",
				}}
				exact
				to={`/manager/${to}`}
				css={menuAtomBtn}
			>
				{menuName}
				<p>{orderAmount}</p>
			</NavLink>
		</Li>
	);
};

export default ManagerOrderBtnAtom;
