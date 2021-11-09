import React from "react";
import { css } from "@emotion/react";
import { Pagination } from "@mui/material";
import { RouteComponentProps } from "react-router-dom";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopCards from "../../components/organisms/ShopMenu/ShopCards";
import ShopMenuHeader from "../../components/molecules/ShopMenu/ShopMenuHeader";
import ShopHeaderMenu from "../../components/organisms/ShopMenu/ShopHeaderMenu";

const paginationStyle = css`
	.css-1pi9rcw-MuiButtonBase-root-MuiPaginationItem-root.Mui-selected:hover {
		background-color: #54b689;
	}
	.css-10w330c-MuiButtonBase-root-MuiPaginationItem-root.Mui-selected {
		background-color: #54b689;
		color: #fff;
	}

	.css-1pi9rcw-MuiButtonBase-root-MuiPaginationItem-root {
		font-size: 15px;
		font-family: "Pretendard";
		font-weight: 500;
	}
`;

type matchParams = {
	menuName: string;
};

const ShopMenuPage: React.FC<RouteComponentProps<matchParams>> = ({
	match,
}) => {
	return (
		<PageTemplate
			header={<ShopHeaderMenu />}
			footer={
				<Pagination css={paginationStyle} count={1} shape="rounded" />
			}
			isFooter
		>
			<ShopMenuHeader>{match.params.menuName}</ShopMenuHeader>
			<ShopCards />
		</PageTemplate>
	);
};

export default ShopMenuPage;