import React, { useState, useEffect } from "react";
import { css } from "@emotion/react";
import styled from "@emotion/styled";
import { Pagination } from "@mui/material";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../app/store";
import PageTemplate from "../../components/templates/PageTemplate";
import SearchForms from "../../components/organisms/Search/SearchForms";
import SearchCards from "../../components/organisms/Search/SearchCards";
import { selectSearchPage } from "../../features/shopFeatures/updateKeywordsSlice";

const Search: React.FC = () => {
	const [page, setPage] = useState<number>(1);
	const [paginationCount, setPaginationCount] = useState<number>(0);
	const totalNum = useSelector((state: RootState) => {
		return state.updateKeywords;
	});
	const dispatch = useDispatch();

	const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
		setPage(value);
	};

	useEffect(() => {
		const pageCount = Math.ceil(totalNum.totalNum / 10);
		setPaginationCount(pageCount);
	}, [totalNum]);

	useEffect(() => {
		dispatch(selectSearchPage(true));
	}, []);

	return (
		<>
			<HeaderHide />
			<PageTemplate
				header={<SearchForms />}
				isHeader
				isSection={false}
				isFooter={false}
			>
				<SearchCards menuPaginationNumber={page} />
				<Pagination
					css={paginationStyle}
					count={paginationCount}
					shape="rounded"
					onChange={handleChange}
				/>
			</PageTemplate>
		</>
	);
};

const HeaderHide = styled.div`
	position: absolute;
	top: 5px;
	left: 30%;
	width: 600px;
	height: 40px;
	background: #54b689;
	z-index: 100;

	@media (max-width: 900px) {
		left: 40%;
		width: 510px;
		background: #54b689;
	}
`;

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

export default Search;
