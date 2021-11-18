import React from "react";
import { css } from "@emotion/react";

const header = css`
	display: flex;
	align-items: center;
	margin-left: 5px;
	justify-content: space-between;
	padding-bottom: 30px;
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
