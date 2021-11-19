/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Wrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 1180px;
	margin: 0 auto;
	padding-top: 50px;
	justify-content: center;
`;

const Header = styled.header`
	display: flex;
	justify-content: center;
	width: 100%;
`;

const Section = styled.article`
	margin-top: 50px;
	display: flex;
	justify-content: center;
	width: 100%;
	padding: 30px 0 30px 0;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
`;

const Content = styled.section`
	width: 100%;
	box-sizing: border-box; /* box사이즈를 기준으로 요소의 너비와 높이를 계산 */
	margin: 2rem auto;
`;

const Footer = styled.footer`
	margin-top: auto;
	width: 100%;
	display: flex;
	justify-content: center;
	padding-bottom: 2rem;
`;

type Props = {
	header?: React.ReactNode;
	section?: React.ReactNode;
	children: React.ReactNode;
	footer?: React.ReactNode;
	isHeader: boolean;
	isFooter: boolean; // footer 사용 안하시려면 isFooter={false}하시고 footer는 안쓰셔도 됩니다.
	isSection: boolean;
};

const PageTemplate: React.FC<Props> = ({
	header,
	section,
	children,
	footer,
	isHeader,
	isFooter,
	isSection,
	...props
}) => {
	return (
		<Wrapper {...props}>
			{isHeader ? <Header>{header}</Header> : null}
			{isSection ? <Section>{section}</Section> : null}
			<Content>{children}</Content>
			{isFooter ? <Footer>{footer}</Footer> : null}
		</Wrapper>
	);
};

PageTemplate.defaultProps = {
	header: null,
	section: null,
	footer: null,
};

export default PageTemplate;
