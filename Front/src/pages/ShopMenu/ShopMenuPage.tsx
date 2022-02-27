import React, { useEffect, useState } from "react";
import { css } from "@emotion/react";
import { Pagination } from "@mui/material";
import { RouteComponentProps } from "react-router-dom";
import axios from "axios";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopCards from "../../components/organisms/ShopMenu/ShopCards";
import ShopMenuHeader from "../../components/molecules/ShopMenu/ShopMenuHeader";
import Categories from "../../components/organisms/Categories/Categories";

const paginationStyle = css`
	display: flex;
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
	sorting: string;
};

const ShopMenuPage: React.FC<RouteComponentProps<matchParams>> = ({
	match,
}) => {
	// For pagination
	const [page, setPage] = useState<number>(1);
	const [paginationCount, setPaginationCount] = useState<number>(0);
	const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
		setPage(value);
	};

	// getting api's length from product to determine the count number for pagination
	// 수호님이 따로 api만들어서, length를 가져올 수 있게 하실거임. 기다리자구~
	async function getLengthOfProducts() {
		return axios.get(
			`http://54.180.134.20/api/products?category=${
				match.params.menuName === "전체보기"
					? ""
					: match.params.menuName
			}`
		);
	}

	useEffect(() => {
		getLengthOfProducts()
			.then((res) => {
				console.log("lengthOfProductsFunctionResponse:", res);
			})
			.catch((error) => {
				console.error(error);
			});
	}, [match]);

	console.log("match: ", match);
	return (
		<PageTemplate
			header={<Categories />}
			isHeader
			isSection={false}
			footer={
				<Pagination
					css={paginationStyle}
					count={10}
					shape="rounded"
					onChange={handleChange}
				/>
			}
			isFooter
		>
			<ShopMenuHeader menuName={match.params.menuName || "전체보기"}>
				{match.params.menuName || "전체보기"}
			</ShopMenuHeader>
			<ShopCards
				menuMatchName={match.params.menuName || ""}
				menuSorting={match.params.sorting}
				menuPaginationNumber={page}
			/>
			{console.log(match)}
		</PageTemplate>
	);
};

export default ShopMenuPage;
