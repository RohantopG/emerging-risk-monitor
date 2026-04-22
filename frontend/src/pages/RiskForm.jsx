import { useState } from "react";

function RiskForm() {
  const [name, setName] = useState("");
  const [level, setLevel] = useState("");
  const [errors, setErrors] = useState({});

  const handleSubmit = (e) => {
    e.preventDefault();

    let newErrors = {};

    if (!name) newErrors.name = "Name is required";
    if (!level) newErrors.level = "Risk level is required";

    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      alert("Form submitted successfully");
    }
  };

  return (
    <div className="p-5">
      <h2 className="text-xl font-bold mb-4">Create Risk</h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        
        {/* Name */}
        <div>
          <input
            type="text"
            placeholder="Enter risk name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="border p-2 w-full"
          />
          {errors.name && <p className="text-red-500">{errors.name}</p>}
        </div>

        {/* Risk Level */}
        <div>
          <select
            value={level}
            onChange={(e) => setLevel(e.target.value)}
            className="border p-2 w-full"
          >
            <option value="">Select Risk Level</option>
            <option value="High">High</option>
            <option value="Medium">Medium</option>
            <option value="Low">Low</option>
          </select>
          {errors.level && <p className="text-red-500">{errors.level}</p>}
        </div>

        {/* Submit */}
        <button className="bg-blue-500 text-white px-4 py-2">
          Submit
        </button>
      </form>
    </div>
  );
}

export default RiskForm;