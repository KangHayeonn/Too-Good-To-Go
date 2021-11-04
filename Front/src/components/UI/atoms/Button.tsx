/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";

const style = css`
  &:hover {
    background-color: #777;
  }
`;
const CasualButton: React.FC = ({ ...props }) => {
  const { children } = props;
  return (
    <div>
      <button type="button">Hi</button>
    </div>
  );
};

export default CasualButton;
