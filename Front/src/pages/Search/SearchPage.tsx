import React from "react";
import styled from "@emotion/styled";
import PageTemplate from "../../components/templates/PageTemplate";
import SearchForms from "../../components/organisms/Search/SearchForms";
import SearchCards from "../../components/organisms/Search/SearchCards";

const Search: React.FC = () => {
	return (
		<>
			<HeaderHide />
			<PageTemplate
				header={<SearchForms />}
				isHeader
				isSection={false}
				isFooter={false}
			>
				<SearchCards />
			</PageTemplate>
		</>
	);
};

const HeaderHide = styled.div`
	position: absolute;
	top: 5px;
	left: 39%;
	width: 520px;
	height: 40px;
	background: #54B689;
	z-index: 100;

	@media (max-width: 900px) {
		left: 52%;
		width: 510px;
		background: #54B689;
	}
`

export default Search;
