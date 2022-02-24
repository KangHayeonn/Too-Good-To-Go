import React from "react";
import { css } from "@emotion/react";
import { NavLink } from "react-router-dom";

const header = css`
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding-top: 40px;
	padding-bottom: 40px;
	border-top: 1px solid #eee;
`;

const headerText = css`
	position: relative;
	font-size: 36px;
	font-weight: 800;
	color: #333;
`;

const arrayText = css`
	font-size: 14px;
	font-weight: 300;
	color: #999;
	cursor: pointer;
	padding: 0 10px;
	border-left: 1px solid #999;
	&:first-of-type {
		border: none;
	}
`;

const arrayDiv = css`
	display: flex;
`;

type shopMenuType = {
	children: React.ReactNode;
	menuName: string;
};

const ShopMenuHeader: React.FC<shopMenuType> = ({ children, menuName }) => {
	return (
		<div css={header}>
			<h1 css={headerText}>{children}</h1>
			<div css={arrayDiv}>
				<NavLink
					activeStyle={{
						fontWeight: "400",
						color: "#333",
					}}
					css={arrayText}
					to={`/shopmenu/${menuName}/discount`}
				>
					가격낮은순
				</NavLink>
				<NavLink
					activeStyle={{
						fontWeight: "400",
						color: "#333",
					}}
					css={arrayText}
					to={`/shopmenu/${menuName}/rate`}
				>
					할인율순
				</NavLink>
			</div>
		</div>
	);
};

export default ShopMenuHeader;
