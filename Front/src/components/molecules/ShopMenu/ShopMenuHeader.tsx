import React from "react";
import { css } from "@emotion/react";

const header = css`
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding-bottom: 30px;
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
		color: #333;
		font-weight: 400;
	}
`;

const arrayDiv = css`
	display: flex;
`;

type shopMenuType = {
	children: React.ReactNode;
};

const ShopMenuHeader: React.FC<shopMenuType> = ({ children }) => {
	return (
		<div css={header}>
			<h1 css={headerText}>{children}</h1>
			<div css={arrayDiv}>
				<p css={arrayText}>최신순</p>
				<p css={arrayText}>가격낮은순</p>
				<p css={arrayText}>가격높은순</p>
				<p css={arrayText}>할인률순</p>
			</div>
		</div>
	);
};

export default ShopMenuHeader;
