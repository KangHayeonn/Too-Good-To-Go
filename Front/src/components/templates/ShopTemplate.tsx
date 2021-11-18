/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Wrapper = styled.div`
	display: flex;
	flex-direction: row; // 세로방향
	width: 95%;
	margin: 0 auto;
	display: flex;
	justify-content: center;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.2);
	padding: 1.7em;
`;

const Menu = styled.div`
	margin-left: 30px;
	width: 70%;
`;

const Sidebar = styled.div`
	//box-sizing: border-box; /* box사이즈를 기준으로 요소의 너비와 높이를 계산 */
	margin: 1px auto;
	padding: 1.5em;
`;

type Props = {
	menu: React.ReactNode;
	sidebar: React.ReactNode;
};

const TitleTemplate: React.FC<Props> = ({ menu, sidebar, ...props }) => {
	return (
		<Wrapper {...props}>
			<Menu>{menu}</Menu>
			<Sidebar>{sidebar}</Sidebar>
		</Wrapper>
	);
};

export default TitleTemplate;
