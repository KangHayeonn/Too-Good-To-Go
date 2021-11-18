import React from "react";
import { css } from "@emotion/react";
import { Pagination } from "@mui/material";
import { RouteComponentProps } from "react-router-dom";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopCards from "../../components/organisms/ShopMenu/ShopCards";
import ShopMenuHeader from "../../components/molecules/ShopMenu/ShopMenuHeader";
import Categories from "../../components/organisms/Categories/Categories";

const paginationStyle = css`
	display:flex;
	justify-content: center;
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
			header={<Categories />}
			isHeader
			isSection={false}
			footer={
				<Pagination css={paginationStyle} count={1} shape="rounded" />
			}
			isFooter
		>
			<ShopMenuHeader>
				{match.params.menuName || "전체보기"}
			</ShopMenuHeader>
			<ShopCards menuMatchName={match.params.menuName || "전체보기"} />
		</PageTemplate>
	);
};

export default ShopMenuPage;
