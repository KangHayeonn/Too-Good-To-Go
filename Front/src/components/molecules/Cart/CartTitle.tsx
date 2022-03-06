import React from "react";
import { css } from "@emotion/react";

const header = css`
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin-top: 20px;
	margin-left: 5px;
	padding-top: 60px;
	padding-bottom: 35px;
	border-top: 1px solid #eee;
`;

const headerText = css`
	position: relative;
	font-size: 36px;
	font-weight: 800;
	color: #333;
`;

type shopMenuType = {
	children: React.ReactNode;
};

const ShopMenuHeader: React.FC<shopMenuType> = ({ children }) => {
	return (
		<div css={header}>
			<h1 css={headerText}>{children}</h1>
		</div>
	);
};

export default ShopMenuHeader;
