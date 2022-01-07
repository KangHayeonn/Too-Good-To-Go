import React from "react";
import styled from "@emotion/styled";

const Wrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 1180px;
	margin: 0 auto;
	padding-top: 50px;
	padding-bottom: 50px;
	justify-content: center;
`;

const Header = styled.header`
	display: flex;
	justify-content: center;
	width: 100%;
	flex-wrap: wrap;
	position: relative;
	margin-bottom: 20px;
`;

const Content = styled.section`
	width: 100%;
	box-sizing: border-box; /* box사이즈를 기준으로 요소의 너비와 높이를 계산 */
`;

type Props = {
	header: React.ReactNode;
	children: React.ReactNode;
};

const ManagerTemplate: React.FC<Props> = ({ header, children }) => {
	return (
		<Wrapper>
			<Header>{header}</Header>
			<Content>{children}</Content>
		</Wrapper>
	);
};

export default ManagerTemplate;
