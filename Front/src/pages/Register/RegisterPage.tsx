import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import Register from "./Register";

const RegisterPage: React.FC = () => {
	return (
		<PageTemplate
			isHeader={false}
			section={<Register />}
			isSection
			isFooter={false}
		>
			<></>
		</PageTemplate>
	);
};

export default RegisterPage;
